import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IDynamicData, DynamicData } from 'app/shared/model/dynamic-data.model';
import { DynamicDataService } from './dynamic-data.service';
import { IAuditInfo } from 'app/shared/model/audit-info.model';
import { AuditInfoService } from 'app/entities/audit-info/audit-info.service';
import { IDynamicDataEnvelope } from 'app/shared/model/dynamic-data-envelope.model';
import { DynamicDataEnvelopeService } from 'app/entities/dynamic-data-envelope/dynamic-data-envelope.service';

type SelectableEntity = IAuditInfo | IDynamicDataEnvelope;

@Component({
  selector: 'jhi-dynamic-data-update',
  templateUrl: './dynamic-data-update.component.html',
})
export class DynamicDataUpdateComponent implements OnInit {
  isSaving = false;
  auditinfos: IAuditInfo[] = [];
  dynamicdataenvelopes: IDynamicDataEnvelope[] = [];

  editForm = this.fb.group({
    id: [],
    dataKey: [],
    dataValue: [],
    valueDataType: [],
    auditInfo: [],
    dynamicDataEnvelope: [],
  });

  constructor(
    protected dynamicDataService: DynamicDataService,
    protected auditInfoService: AuditInfoService,
    protected dynamicDataEnvelopeService: DynamicDataEnvelopeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dynamicData }) => {
      this.updateForm(dynamicData);

      this.auditInfoService
        .query({ filter: 'dynamicdata-is-null' })
        .pipe(
          map((res: HttpResponse<IAuditInfo[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAuditInfo[]) => {
          if (!dynamicData.auditInfo || !dynamicData.auditInfo.id) {
            this.auditinfos = resBody;
          } else {
            this.auditInfoService
              .find(dynamicData.auditInfo.id)
              .pipe(
                map((subRes: HttpResponse<IAuditInfo>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAuditInfo[]) => (this.auditinfos = concatRes));
          }
        });

      this.dynamicDataEnvelopeService
        .query()
        .subscribe((res: HttpResponse<IDynamicDataEnvelope[]>) => (this.dynamicdataenvelopes = res.body || []));
    });
  }

  updateForm(dynamicData: IDynamicData): void {
    this.editForm.patchValue({
      id: dynamicData.id,
      dataKey: dynamicData.dataKey,
      dataValue: dynamicData.dataValue,
      valueDataType: dynamicData.valueDataType,
      auditInfo: dynamicData.auditInfo,
      dynamicDataEnvelope: dynamicData.dynamicDataEnvelope,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dynamicData = this.createFromForm();
    if (dynamicData.id !== undefined) {
      this.subscribeToSaveResponse(this.dynamicDataService.update(dynamicData));
    } else {
      this.subscribeToSaveResponse(this.dynamicDataService.create(dynamicData));
    }
  }

  private createFromForm(): IDynamicData {
    return {
      ...new DynamicData(),
      id: this.editForm.get(['id'])!.value,
      dataKey: this.editForm.get(['dataKey'])!.value,
      dataValue: this.editForm.get(['dataValue'])!.value,
      valueDataType: this.editForm.get(['valueDataType'])!.value,
      auditInfo: this.editForm.get(['auditInfo'])!.value,
      dynamicDataEnvelope: this.editForm.get(['dynamicDataEnvelope'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDynamicData>>): void {
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
