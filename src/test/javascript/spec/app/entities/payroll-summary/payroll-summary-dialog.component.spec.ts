/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { PayrollSummaryDialogComponent } from '../../../../../../main/webapp/app/entities/payroll-summary/payroll-summary-dialog.component';
import { PayrollSummaryService } from '../../../../../../main/webapp/app/entities/payroll-summary/payroll-summary.service';
import { PayrollSummary } from '../../../../../../main/webapp/app/entities/payroll-summary/payroll-summary.model';

describe('Component Tests', () => {

    describe('PayrollSummary Management Dialog Component', () => {
        let comp: PayrollSummaryDialogComponent;
        let fixture: ComponentFixture<PayrollSummaryDialogComponent>;
        let service: PayrollSummaryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [PayrollSummaryDialogComponent],
                providers: [
                    PayrollSummaryService
                ]
            })
            .overrideTemplate(PayrollSummaryDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayrollSummaryDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollSummaryService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PayrollSummary(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.payrollSummary = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'payrollSummaryListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PayrollSummary();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.payrollSummary = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'payrollSummaryListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
