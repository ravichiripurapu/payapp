/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { DeductionSubTypeDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/deduction-sub-type/deduction-sub-type-delete-dialog.component';
import { DeductionSubTypeService } from '../../../../../../main/webapp/app/entities/deduction-sub-type/deduction-sub-type.service';

describe('Component Tests', () => {

    describe('DeductionSubType Management Delete Component', () => {
        let comp: DeductionSubTypeDeleteDialogComponent;
        let fixture: ComponentFixture<DeductionSubTypeDeleteDialogComponent>;
        let service: DeductionSubTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [DeductionSubTypeDeleteDialogComponent],
                providers: [
                    DeductionSubTypeService
                ]
            })
            .overrideTemplate(DeductionSubTypeDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DeductionSubTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeductionSubTypeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
