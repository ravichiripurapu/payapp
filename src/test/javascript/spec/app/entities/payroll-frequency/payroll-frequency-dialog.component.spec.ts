/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { PayrollFrequencyDialogComponent } from '../../../../../../main/webapp/app/entities/payroll-frequency/payroll-frequency-dialog.component';
import { PayrollFrequencyService } from '../../../../../../main/webapp/app/entities/payroll-frequency/payroll-frequency.service';
import { PayrollFrequency } from '../../../../../../main/webapp/app/entities/payroll-frequency/payroll-frequency.model';

describe('Component Tests', () => {

    describe('PayrollFrequency Management Dialog Component', () => {
        let comp: PayrollFrequencyDialogComponent;
        let fixture: ComponentFixture<PayrollFrequencyDialogComponent>;
        let service: PayrollFrequencyService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [PayrollFrequencyDialogComponent],
                providers: [
                    PayrollFrequencyService
                ]
            })
            .overrideTemplate(PayrollFrequencyDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayrollFrequencyDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollFrequencyService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PayrollFrequency(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.payrollFrequency = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'payrollFrequencyListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PayrollFrequency();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.payrollFrequency = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'payrollFrequencyListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
