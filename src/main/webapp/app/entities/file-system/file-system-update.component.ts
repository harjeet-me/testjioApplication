import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IFileSystem, FileSystem } from 'app/shared/model/file-system.model';
import { FileSystemService } from './file-system.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IDynamicDataEnvelope } from 'app/shared/model/dynamic-data-envelope.model';
import { DynamicDataEnvelopeService } from 'app/entities/dynamic-data-envelope/dynamic-data-envelope.service';
import { IAuditInfo } from 'app/shared/model/audit-info.model';
import { AuditInfoService } from 'app/entities/audit-info/audit-info.service';
import { IDiscrim } from 'app/shared/model/discrim.model';
import { DiscrimService } from 'app/entities/discrim/discrim.service';
import { IEnvelope } from 'app/shared/model/envelope.model';
import { EnvelopeService } from 'app/entities/envelope/envelope.service';

type SelectableEntity = IDynamicDataEnvelope | IAuditInfo | IDiscrim | IEnvelope;

@Component({
  selector: 'jhi-file-system-update',
  templateUrl: './file-system-update.component.html',
})
export class FileSystemUpdateComponent implements OnInit {
  isSaving = false;
  dynamicdataenvelopes: IDynamicDataEnvelope[] = [];
  auditinfos: IAuditInfo[] = [];
  discrims: IDiscrim[] = [];
  envelopes: IEnvelope[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    data: [],
    dataContentType: [],
    ext: [],
    url: [],
    dynamicDataEnvelope: [],
    auditInfo: [],
    discrim: [],
    envelope: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected fileSystemService: FileSystemService,
    protected dynamicDataEnvelopeService: DynamicDataEnvelopeService,
    protected auditInfoService: AuditInfoService,
    protected discrimService: DiscrimService,
    protected envelopeService: EnvelopeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fileSystem }) => {
      this.updateForm(fileSystem);

      this.dynamicDataEnvelopeService
        .query({ filter: 'filesystem-is-null' })
        .pipe(
          map((res: HttpResponse<IDynamicDataEnvelope[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDynamicDataEnvelope[]) => {
          if (!fileSystem.dynamicDataEnvelope || !fileSystem.dynamicDataEnvelope.id) {
            this.dynamicdataenvelopes = resBody;
          } else {
            this.dynamicDataEnvelopeService
              .find(fileSystem.dynamicDataEnvelope.id)
              .pipe(
                map((subRes: HttpResponse<IDynamicDataEnvelope>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDynamicDataEnvelope[]) => (this.dynamicdataenvelopes = concatRes));
          }
        });

      this.auditInfoService
        .query({ filter: 'filesystem-is-null' })
        .pipe(
          map((res: HttpResponse<IAuditInfo[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAuditInfo[]) => {
          if (!fileSystem.auditInfo || !fileSystem.auditInfo.id) {
            this.auditinfos = resBody;
          } else {
            this.auditInfoService
              .find(fileSystem.auditInfo.id)
              .pipe(
                map((subRes: HttpResponse<IAuditInfo>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAuditInfo[]) => (this.auditinfos = concatRes));
          }
        });

      this.discrimService.query().subscribe((res: HttpResponse<IDiscrim[]>) => (this.discrims = res.body || []));

      this.envelopeService.query().subscribe((res: HttpResponse<IEnvelope[]>) => (this.envelopes = res.body || []));
    });
  }

  updateForm(fileSystem: IFileSystem): void {
    this.editForm.patchValue({
      id: fileSystem.id,
      name: fileSystem.name,
      data: fileSystem.data,
      dataContentType: fileSystem.dataContentType,
      ext: fileSystem.ext,
      url: fileSystem.url,
      dynamicDataEnvelope: fileSystem.dynamicDataEnvelope,
      auditInfo: fileSystem.auditInfo,
      discrim: fileSystem.discrim,
      envelope: fileSystem.envelope,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('testjioApplicationApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fileSystem = this.createFromForm();
    if (fileSystem.id !== undefined) {
      this.subscribeToSaveResponse(this.fileSystemService.update(fileSystem));
    } else {
      this.subscribeToSaveResponse(this.fileSystemService.create(fileSystem));
    }
  }

  private createFromForm(): IFileSystem {
    return {
      ...new FileSystem(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      dataContentType: this.editForm.get(['dataContentType'])!.value,
      data: this.editForm.get(['data'])!.value,
      ext: this.editForm.get(['ext'])!.value,
      url: this.editForm.get(['url'])!.value,
      dynamicDataEnvelope: this.editForm.get(['dynamicDataEnvelope'])!.value,
      auditInfo: this.editForm.get(['auditInfo'])!.value,
      discrim: this.editForm.get(['discrim'])!.value,
      envelope: this.editForm.get(['envelope'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFileSystem>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
