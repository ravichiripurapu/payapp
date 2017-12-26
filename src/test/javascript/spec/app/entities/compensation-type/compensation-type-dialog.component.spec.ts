/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { CompensationTypeDialogComponent } from '../../../../../../main/webapp/app/entities/compensation-type/compensation-type-dialog.component';
import { CompensationTypeService } from '../../../../../../main/webapp/app/entities/compensation-type/compensation-type.service';
import { CompensationType } from '../../../../../../main/webapp/app/entities/compensation-type/compensation-type.model';

describe('Component Tests', () => {

    describe('CompensationType Management Dialog Component', () => {
        let comp: CompensationTypeDialogComponent;
        let fixture: ComponentFixture<CompensationTypeDialogComponent>;
        let service: CompensationTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompensationTypeDialogComponent],
                providers: [
                    CompensationTypeService
                ]
            })
            .overrideTemplate(CompensationTypeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompensationTypeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompensationTypeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompensationType(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.compensationType = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'compensationTypeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CompensationType();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.compensationType = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'compensationTypeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
