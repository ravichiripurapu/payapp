/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { PayrollEmployerTaxesDialogComponent } from '../../../../../../main/webapp/app/entities/payroll-employer-taxes/payroll-employer-taxes-dialog.component';
import { PayrollEmployerTaxesService } from '../../../../../../main/webapp/app/entities/payroll-employer-taxes/payroll-employer-taxes.service';
import { PayrollEmployerTaxes } from '../../../../../../main/webapp/app/entities/payroll-employer-taxes/payroll-employer-taxes.model';

describe('Component Tests', () => {

    describe('PayrollEmployerTaxes Management Dialog Component', () => {
        let comp: PayrollEmployerTaxesDialogComponent;
        let fixture: ComponentFixture<PayrollEmployerTaxesDialogComponent>;
        let service: PayrollEmployerTaxesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [PayrollEmployerTaxesDialogComponent],
                providers: [
                    PayrollEmployerTaxesService
                ]
            })
            .overrideTemplate(PayrollEmployerTaxesDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayrollEmployerTaxesDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollEmployerTaxesService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PayrollEmployerTaxes(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.payrollEmployerTaxes = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'payrollEmployerTaxesListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PayrollEmployerTaxes();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.payrollEmployerTaxes = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'payrollEmployerTaxesListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
