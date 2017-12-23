/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { PayrollEmployeeDialogComponent } from '../../../../../../main/webapp/app/entities/payroll-employee/payroll-employee-dialog.component';
import { PayrollEmployeeService } from '../../../../../../main/webapp/app/entities/payroll-employee/payroll-employee.service';
import { PayrollEmployee } from '../../../../../../main/webapp/app/entities/payroll-employee/payroll-employee.model';

describe('Component Tests', () => {

    describe('PayrollEmployee Management Dialog Component', () => {
        let comp: PayrollEmployeeDialogComponent;
        let fixture: ComponentFixture<PayrollEmployeeDialogComponent>;
        let service: PayrollEmployeeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [PayrollEmployeeDialogComponent],
                providers: [
                    PayrollEmployeeService
                ]
            })
            .overrideTemplate(PayrollEmployeeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayrollEmployeeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollEmployeeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PayrollEmployee(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.payrollEmployee = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'payrollEmployeeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PayrollEmployee();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.payrollEmployee = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'payrollEmployeeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
