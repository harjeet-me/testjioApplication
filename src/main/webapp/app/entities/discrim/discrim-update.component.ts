import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDiscrim, Discrim } from 'app/shared/model/discrim.model';
import { DiscrimService } from './discrim.service';

@Component({
  selector: 'jhi-discrim-update',
  templateUrl: './discrim-update.component.html',
})
export class DiscrimUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
  });

  constructor(protected discrimService: DiscrimService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ discrim }) => {
      this.updateForm(discrim);
    });
  }

  updateForm(discrim: IDiscrim): void {
    this.editForm.patchValue({
      id: discrim.id,
      name: discrim.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const discrim = this.createFromForm();
    if (discrim.id !== undefined) {
      this.subscribeToSaveResponse(this.discrimService.update(discrim));
    } else {
      this.subscribeToSaveResponse(this.discrimService.create(discrim));
    }
  }

  private createFromForm(): IDiscrim {
    return {
      ...new Discrim(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDiscrim>>): void {
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
