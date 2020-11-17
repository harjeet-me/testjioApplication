import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IDynamicDataEnvelope, DynamicDataEnvelope } from 'app/shared/model/dynamic-data-envelope.model';
import { DynamicDataEnvelopeService } from './dynamic-data-envelope.service';
import { IAuditInfo } from 'app/shared/model/audit-info.model';
import { AuditInfoService } from 'app/entities/audit-info/audit-info.service';

@Component({
  selector: 'jhi-dynamic-data-envelope-update',
  templateUrl: './dynamic-data-envelope-update.component.html',
})
export class DynamicDataEnvelopeUpdateComponent implements OnInit {
  isSaving = false;
  auditinfos: IAuditInfo[] = [];

  editForm = this.fb.group({
    id: [],
    desc: [],
    auditInfo: [],
  });

  constructor(
    protected dynamicDataEnvelopeService: DynamicDataEnvelopeService,
    protected auditInfoService: AuditInfoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dynamicDataEnvelope }) => {
      this.updateForm(dynamicDataEnvelope);

      this.auditInfoService
        .query({ filter: 'dynamicdataenvelope-is-null' })
        .pipe(
          map((res: HttpResponse<IAuditInfo[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAuditInfo[]) => {
          if (!dynamicDataEnvelope.auditInfo || !dynamicDataEnvelope.auditInfo.id) {
            this.auditinfos = resBody;
          } else {
            this.auditInfoService
              .find(dynamicDataEnvelope.auditInfo.id)
              .pipe(
                map((subRes: HttpResponse<IAuditInfo>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAuditInfo[]) => (this.auditinfos = concatRes));
          }
        });
    });
  }

  updateForm(dynamicDataEnvelope: IDynamicDataEnvelope): void {
    this.editForm.patchValue({
      id: dynamicDataEnvelope.id,
      desc: dynamicDataEnvelope.desc,
      auditInfo: dynamicDataEnvelope.auditInfo,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dynamicDataEnvelope = this.createFromForm();
    if (dynamicDataEnvelope.id !== undefined) {
      this.subscribeToSaveResponse(this.dynamicDataEnvelopeService.update(dynamicDataEnvelope));
    } else {
      this.subscribeToSaveResponse(this.dynamicDataEnvelopeService.create(dynamicDataEnvelope));
    }
  }

  private createFromForm(): IDynamicDataEnvelope {
    return {
      ...new DynamicDataEnvelope(),
      id: this.editForm.get(['id'])!.value,
      desc: this.editForm.get(['desc'])!.value,
      auditInfo: this.editForm.get(['auditInfo'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDynamicDataEnvelope>>): void {
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

  trackById(index: number, item: IAuditInfo): any {
    return item.id;
  }
}
