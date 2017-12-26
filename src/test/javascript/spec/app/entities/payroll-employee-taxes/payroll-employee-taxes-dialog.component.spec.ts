/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { PayrollEmployeeTaxesDialogComponent } from '../../../../../../main/webapp/app/entities/payroll-employee-taxes/payroll-employee-taxes-dialog.component';
import { PayrollEmployeeTaxesService } from '../../../../../../main/webapp/app/entities/payroll-employee-taxes/payroll-employee-taxes.service';
import { PayrollEmployeeTaxes } from '../../../../../../main/webapp/app/entities/payroll-employee-taxes/payroll-employee-taxes.model';

describe('Component Tests', () => {

    describe('PayrollEmployeeTaxes Management Dialog Component', () => {
        let comp: PayrollEmployeeTaxesDialogComponent;
        let fixture: ComponentFixture<PayrollEmployeeTaxesDialogComponent>;
        let service: PayrollEmployeeTaxesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [PayrollEmployeeTaxesDialogComponent],
                providers: [
                    PayrollEmployeeTaxesService
                ]
            })
            .overrideTemplate(PayrollEmployeeTaxesDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayrollEmployeeTaxesDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollEmployeeTaxesService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PayrollEmployeeTaxes(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.payrollEmployeeTaxes = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'payrollEmployeeTaxesListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PayrollEmployeeTaxes();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.payrollEmployeeTaxes = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'payrollEmployeeTaxesListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
