/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { PayrollEarningsDialogComponent } from '../../../../../../main/webapp/app/entities/payroll-earnings/payroll-earnings-dialog.component';
import { PayrollEarningsService } from '../../../../../../main/webapp/app/entities/payroll-earnings/payroll-earnings.service';
import { PayrollEarnings } from '../../../../../../main/webapp/app/entities/payroll-earnings/payroll-earnings.model';

describe('Component Tests', () => {

    describe('PayrollEarnings Management Dialog Component', () => {
        let comp: PayrollEarningsDialogComponent;
        let fixture: ComponentFixture<PayrollEarningsDialogComponent>;
        let service: PayrollEarningsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [PayrollEarningsDialogComponent],
                providers: [
                    PayrollEarningsService
                ]
            })
            .overrideTemplate(PayrollEarningsDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayrollEarningsDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollEarningsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PayrollEarnings(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.employeePayEarning = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'payrollEarningsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PayrollEarnings();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.employeePayEarning = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'payrollEarningsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
