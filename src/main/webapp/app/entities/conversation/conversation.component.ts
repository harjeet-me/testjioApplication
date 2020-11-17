import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConversation } from 'app/shared/model/conversation.model';
import { ConversationService } from './conversation.service';
import { ConversationDeleteDialogComponent } from './conversation-delete-dialog.component';

@Component({
  selector: 'jhi-conversation',
  templateUrl: './conversation.component.html',
})
export class ConversationComponent implements OnInit, OnDestroy {
  conversations?: IConversation[];
  eventSubscriber?: Subscription;

  constructor(
    protected conversationService: ConversationService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.conversationService.query().subscribe((res: HttpResponse<IConversation[]>) => (this.conversations = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConversations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConversation): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInConversations(): void {
    this.eventSubscriber = this.eventManager.subscribe('conversationListModification', () => this.loadAll());
  }

  delete(conversation: IConversation): void {
    const modalRef = this.modalService.open(ConversationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.conversation = conversation;
  }
}
