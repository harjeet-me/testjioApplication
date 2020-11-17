import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IEnvelope, Envelope } from 'app/shared/model/envelope.model';
import { EnvelopeService } from './envelope.service';
import { IDynamicDataEnvelope } from 'app/shared/model/dynamic-data-envelope.model';
import { DynamicDataEnvelopeService } from 'app/entities/dynamic-data-envelope/dynamic-data-envelope.service';
import { IAuditInfo } from 'app/shared/model/audit-info.model';
import { AuditInfoService } from 'app/entities/audit-info/audit-info.service';
import { IDiscrim } from 'app/shared/model/discrim.model';
import { DiscrimService } from 'app/entities/discrim/discrim.service';

type SelectableEntity = IDynamicDataEnvelope | IAuditInfo | IDiscrim | IEnvelope;

@Component({
  selector: 'jhi-envelope-update',
  templateUrl: './envelope-update.component.html',
})
export class EnvelopeUpdateComponent implements OnInit {
  isSaving = false;
  dynamicdataenvelopes: IDynamicDataEnvelope[] = [];
  auditinfos: IAuditInfo[] = [];
  discrims: IDiscrim[] = [];
  envelopes: IEnvelope[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    desc: [],
    dynamicDataEnvelope: [],
    auditInfo: [],
    discrim: [],
    owner: [],
  });

  constructor(
    protected envelopeService: EnvelopeService,
    protected dynamicDataEnvelopeService: DynamicDataEnvelopeService,
    protected auditInfoService: AuditInfoService,
    protected discrimService: DiscrimService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ envelope }) => {
      this.updateForm(envelope);

      this.dynamicDataEnvelopeService
        .query({ filter: 'envelope-is-null' })
        .pipe(
          map((res: HttpResponse<IDynamicDataEnvelope[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDynamicDataEnvelope[]) => {
          if (!envelope.dynamicDataEnvelope || !envelope.dynamicDataEnvelope.id) {
            this.dynamicdataenvelopes = resBody;
          } else {
            this.dynamicDataEnvelopeService
              .find(envelope.dynamicDataEnvelope.id)
              .pipe(
                map((subRes: HttpResponse<IDynamicDataEnvelope>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDynamicDataEnvelope[]) => (this.dynamicdataenvelopes = concatRes));
          }
        });

      this.auditInfoService
        .query({ filter: 'envelope-is-null' })
        .pipe(
          map((res: HttpResponse<IAuditInfo[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAuditInfo[]) => {
          if (!envelope.auditInfo || !envelope.auditInfo.id) {
            this.auditinfos = resBody;
          } else {
            this.auditInfoService
              .find(envelope.auditInfo.id)
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

  updateForm(envelope: IEnvelope): void {
    this.editForm.patchValue({
      id: envelope.id,
      name: envelope.name,
      desc: envelope.desc,
      dynamicDataEnvelope: envelope.dynamicDataEnvelope,
      auditInfo: envelope.auditInfo,
      discrim: envelope.discrim,
      owner: envelope.owner,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const envelope = this.createFromForm();
    if (envelope.id !== undefined) {
      this.subscribeToSaveResponse(this.envelopeService.update(envelope));
    } else {
      this.subscribeToSaveResponse(this.envelopeService.create(envelope));
    }
  }

  private createFromForm(): IEnvelope {
    return {
      ...new Envelope(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      desc: this.editForm.get(['desc'])!.value,
      dynamicDataEnvelope: this.editForm.get(['dynamicDataEnvelope'])!.value,
      auditInfo: this.editForm.get(['auditInfo'])!.value,
      discrim: this.editForm.get(['discrim'])!.value,
      owner: this.editForm.get(['owner'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnvelope>>): void {
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
