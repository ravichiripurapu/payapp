/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { DeductionTypeDialogComponent } from '../../../../../../main/webapp/app/entities/deduction-type/deduction-type-dialog.component';
import { DeductionTypeService } from '../../../../../../main/webapp/app/entities/deduction-type/deduction-type.service';
import { DeductionType } from '../../../../../../main/webapp/app/entities/deduction-type/deduction-type.model';

describe('Component Tests', () => {

    describe('DeductionType Management Dialog Component', () => {
        let comp: DeductionTypeDialogComponent;
        let fixture: ComponentFixture<DeductionTypeDialogComponent>;
        let service: DeductionTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [DeductionTypeDialogComponent],
                providers: [
                    DeductionTypeService
                ]
            })
            .overrideTemplate(DeductionTypeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DeductionTypeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeductionTypeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DeductionType(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.deductionType = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'deductionTypeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DeductionType();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.deductionType = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'deductionTypeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
