import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestjioApplicationTestModule } from '../../../test.module';
import { FileSystemComponent } from 'app/entities/file-system/file-system.component';
import { FileSystemService } from 'app/entities/file-system/file-system.service';
import { FileSystem } from 'app/shared/model/file-system.model';

describe('Component Tests', () => {
  describe('FileSystem Management Component', () => {
    let comp: FileSystemComponent;
    let fixture: ComponentFixture<FileSystemComponent>;
    let service: FileSystemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestjioApplicationTestModule],
        declarations: [FileSystemComponent],
      })
        .overrideTemplate(FileSystemComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FileSystemComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FileSystemService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FileSystem(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.fileSystems && comp.fileSystems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
