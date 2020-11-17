import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFileSystem } from 'app/shared/model/file-system.model';
import { FileSystemService } from './file-system.service';
import { FileSystemDeleteDialogComponent } from './file-system-delete-dialog.component';

@Component({
  selector: 'jhi-file-system',
  templateUrl: './file-system.component.html',
})
export class FileSystemComponent implements OnInit, OnDestroy {
  fileSystems?: IFileSystem[];
  eventSubscriber?: Subscription;

  constructor(
    protected fileSystemService: FileSystemService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.fileSystemService.query().subscribe((res: HttpResponse<IFileSystem[]>) => (this.fileSystems = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFileSystems();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFileSystem): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInFileSystems(): void {
    this.eventSubscriber = this.eventManager.subscribe('fileSystemListModification', () => this.loadAll());
  }

  delete(fileSystem: IFileSystem): void {
    const modalRef = this.modalService.open(FileSystemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.fileSystem = fileSystem;
  }
}
