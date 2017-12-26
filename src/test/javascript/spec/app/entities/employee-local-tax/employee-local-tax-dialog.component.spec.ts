/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { EmployeeLocalTaxDialogComponent } from '../../../../../../main/webapp/app/entities/employee-local-tax/employee-local-tax-dialog.component';
import { EmployeeLocalTaxService } from '../../../../../../main/webapp/app/entities/employee-local-tax/employee-local-tax.service';
import { EmployeeLocalTax } from '../../../../../../main/webapp/app/entities/employee-local-tax/employee-local-tax.model';

describe('Component Tests', () => {

    describe('EmployeeLocalTax Management Dialog Component', () => {
        let comp: EmployeeLocalTaxDialogComponent;
        let fixture: ComponentFixture<EmployeeLocalTaxDialogComponent>;
        let service: EmployeeLocalTaxService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [EmployeeLocalTaxDialogComponent],
                providers: [
                    EmployeeLocalTaxService
                ]
            })
            .overrideTemplate(EmployeeLocalTaxDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeLocalTaxDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeLocalTaxService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new EmployeeLocalTax(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.employeeLocalTax = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'employeeLocalTaxListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new EmployeeLocalTax();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.employeeLocalTax = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'employeeLocalTaxListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
