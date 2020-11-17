import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAuditInfo, AuditInfo } from 'app/shared/model/audit-info.model';
import { AuditInfoService } from './audit-info.service';

@Component({
  selector: 'jhi-audit-info-update',
  templateUrl: './audit-info-update.component.html',
})
export class AuditInfoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    createdDate: [],
    createdBy: [],
    lastModifiedDate: [],
    lastModifiedBy: [],
  });

  constructor(protected auditInfoService: AuditInfoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ auditInfo }) => {
      if (!auditInfo.id) {
        const today = moment().startOf('day');
        auditInfo.createdDate = today;
        auditInfo.lastModifiedDate = today;
      }

      this.updateForm(auditInfo);
    });
  }

  updateForm(auditInfo: IAuditInfo): void {
    this.editForm.patchValue({
      id: auditInfo.id,
      createdDate: auditInfo.createdDate ? auditInfo.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: auditInfo.createdBy,
      lastModifiedDate: auditInfo.lastModifiedDate ? auditInfo.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: auditInfo.lastModifiedBy,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const auditInfo = this.createFromForm();
    if (auditInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.auditInfoService.update(auditInfo));
    } else {
      this.subscribeToSaveResponse(this.auditInfoService.create(auditInfo));
    }
  }

  private createFromForm(): IAuditInfo {
    return {
      ...new AuditInfo(),
      id: this.editForm.get(['id'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuditInfo>>): void {
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
}
