import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IConversation, Conversation } from 'app/shared/model/conversation.model';
import { ConversationService } from './conversation.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IDynamicDataEnvelope } from 'app/shared/model/dynamic-data-envelope.model';
import { DynamicDataEnvelopeService } from 'app/entities/dynamic-data-envelope/dynamic-data-envelope.service';
import { IAuditInfo } from 'app/shared/model/audit-info.model';
import { AuditInfoService } from 'app/entities/audit-info/audit-info.service';
import { IDiscrim } from 'app/shared/model/discrim.model';
import { DiscrimService } from 'app/entities/discrim/discrim.service';

type SelectableEntity = IDynamicDataEnvelope | IAuditInfo | IDiscrim;

@Component({
  selector: 'jhi-conversation-update',
  templateUrl: './conversation-update.component.html',
})
export class ConversationUpdateComponent implements OnInit {
  isSaving = false;
  dynamicdataenvelopes: IDynamicDataEnvelope[] = [];
  auditinfos: IAuditInfo[] = [];
  discrims: IDiscrim[] = [];

  editForm = this.fb.group({
    id: [],
    subject: [],
    type: [],
    attachment: [],
    attachmentContentType: [],
    attachmentName: [],
    status: [],
    sentDateTime: [],
    dynamicDataEnvelope: [],
    auditInfo: [],
    discrim: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected conversationService: ConversationService,
    protected dynamicDataEnvelopeService: DynamicDataEnvelopeService,
    protected auditInfoService: AuditInfoService,
    protected discrimService: DiscrimService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ conversation }) => {
      if (!conversation.id) {
        const today = moment().startOf('day');
        conversation.sentDateTime = today;
      }

      this.updateForm(conversation);

      this.dynamicDataEnvelopeService
        .query({ filter: 'conversation-is-null' })
        .pipe(
          map((res: HttpResponse<IDynamicDataEnvelope[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDynamicDataEnvelope[]) => {
          if (!conversation.dynamicDataEnvelope || !conversation.dynamicDataEnvelope.id) {
            this.dynamicdataenvelopes = resBody;
          } else {
            this.dynamicDataEnvelopeService
              .find(conversation.dynamicDataEnvelope.id)
              .pipe(
                map((subRes: HttpResponse<IDynamicDataEnvelope>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDynamicDataEnvelope[]) => (this.dynamicdataenvelopes = concatRes));
          }
        });

      this.auditInfoService
        .query({ filter: 'conversation-is-null' })
        .pipe(
          map((res: HttpResponse<IAuditInfo[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAuditInfo[]) => {
          if (!conversation.auditInfo || !conversation.auditInfo.id) {
            this.auditinfos = resBody;
          } else {
            this.auditInfoService
              .find(conversation.auditInfo.id)
              .pipe(
                map((subRes: HttpResponse<IAuditInfo>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAuditInfo[]) => (this.auditinfos = concatRes));
          }
        });

      this.discrimService.query().subscribe((res: HttpResponse<IDiscrim[]>) => (this.discrims = res.body || []));
    });
  }

  updateForm(conversation: IConversation): void {
    this.editForm.patchValue({
      id: conversation.id,
      subject: conversation.subject,
      type: conversation.type,
      attachment: conversation.attachment,
      attachmentContentType: conversation.attachmentContentType,
      attachmentName: conversation.attachmentName,
      status: conversation.status,
      sentDateTime: conversation.sentDateTime ? conversation.sentDateTime.format(DATE_TIME_FORMAT) : null,
      dynamicDataEnvelope: conversation.dynamicDataEnvelope,
      auditInfo: conversation.auditInfo,
      discrim: conversation.discrim,
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
    const conversation = this.createFromForm();
    if (conversation.id !== undefined) {
      this.subscribeToSaveResponse(this.conversationService.update(conversation));
    } else {
      this.subscribeToSaveResponse(this.conversationService.create(conversation));
    }
  }

  private createFromForm(): IConversation {
    return {
      ...new Conversation(),
      id: this.editForm.get(['id'])!.value,
      subject: this.editForm.get(['subject'])!.value,
      type: this.editForm.get(['type'])!.value,
      attachmentContentType: this.editForm.get(['attachmentContentType'])!.value,
      attachment: this.editForm.get(['attachment'])!.value,
      attachmentName: this.editForm.get(['attachmentName'])!.value,
      status: this.editForm.get(['status'])!.value,
      sentDateTime: this.editForm.get(['sentDateTime'])!.value
        ? moment(this.editForm.get(['sentDateTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dynamicDataEnvelope: this.editForm.get(['dynamicDataEnvelope'])!.value,
      auditInfo: this.editForm.get(['auditInfo'])!.value,
      discrim: this.editForm.get(['discrim'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConversation>>): void {
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
