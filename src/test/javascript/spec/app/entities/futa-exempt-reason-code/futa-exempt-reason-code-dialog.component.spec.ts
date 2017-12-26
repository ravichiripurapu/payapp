/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { FutaExemptReasonCodeDialogComponent } from '../../../../../../main/webapp/app/entities/futa-exempt-reason-code/futa-exempt-reason-code-dialog.component';
import { FutaExemptReasonCodeService } from '../../../../../../main/webapp/app/entities/futa-exempt-reason-code/futa-exempt-reason-code.service';
import { FutaExemptReasonCode } from '../../../../../../main/webapp/app/entities/futa-exempt-reason-code/futa-exempt-reason-code.model';

describe('Component Tests', () => {

    describe('FutaExemptReasonCode Management Dialog Component', () => {
        let comp: FutaExemptReasonCodeDialogComponent;
        let fixture: ComponentFixture<FutaExemptReasonCodeDialogComponent>;
        let service: FutaExemptReasonCodeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [FutaExemptReasonCodeDialogComponent],
                providers: [
                    FutaExemptReasonCodeService
                ]
            })
            .overrideTemplate(FutaExemptReasonCodeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FutaExemptReasonCodeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FutaExemptReasonCodeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new FutaExemptReasonCode(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.futaExemptReasonCode = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'futaExemptReasonCodeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new FutaExemptReasonCode();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.futaExemptReasonCode = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'futaExemptReasonCodeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
