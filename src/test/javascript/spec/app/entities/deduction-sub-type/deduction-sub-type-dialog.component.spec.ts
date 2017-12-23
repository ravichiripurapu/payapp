/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { DeductionSubTypeDialogComponent } from '../../../../../../main/webapp/app/entities/deduction-sub-type/deduction-sub-type-dialog.component';
import { DeductionSubTypeService } from '../../../../../../main/webapp/app/entities/deduction-sub-type/deduction-sub-type.service';
import { DeductionSubType } from '../../../../../../main/webapp/app/entities/deduction-sub-type/deduction-sub-type.model';

describe('Component Tests', () => {

    describe('DeductionSubType Management Dialog Component', () => {
        let comp: DeductionSubTypeDialogComponent;
        let fixture: ComponentFixture<DeductionSubTypeDialogComponent>;
        let service: DeductionSubTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [DeductionSubTypeDialogComponent],
                providers: [
                    DeductionSubTypeService
                ]
            })
            .overrideTemplate(DeductionSubTypeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DeductionSubTypeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeductionSubTypeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DeductionSubType(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.deductionSubType = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'deductionSubTypeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DeductionSubType();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.deductionSubType = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'deductionSubTypeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
