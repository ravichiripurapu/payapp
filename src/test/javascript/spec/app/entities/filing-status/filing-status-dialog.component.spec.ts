/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { FilingStatusDialogComponent } from '../../../../../../main/webapp/app/entities/filing-status/filing-status-dialog.component';
import { FilingStatusService } from '../../../../../../main/webapp/app/entities/filing-status/filing-status.service';
import { FilingStatus } from '../../../../../../main/webapp/app/entities/filing-status/filing-status.model';

describe('Component Tests', () => {

    describe('FilingStatus Management Dialog Component', () => {
        let comp: FilingStatusDialogComponent;
        let fixture: ComponentFixture<FilingStatusDialogComponent>;
        let service: FilingStatusService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [FilingStatusDialogComponent],
                providers: [
                    FilingStatusService
                ]
            })
            .overrideTemplate(FilingStatusDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FilingStatusDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FilingStatusService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new FilingStatus(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.filingStatus = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'filingStatusListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new FilingStatus();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.filingStatus = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'filingStatusListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
