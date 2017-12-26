/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { EmployeeFederalTaxDialogComponent } from '../../../../../../main/webapp/app/entities/employee-federal-tax/employee-federal-tax-dialog.component';
import { EmployeeFederalTaxService } from '../../../../../../main/webapp/app/entities/employee-federal-tax/employee-federal-tax.service';
import { EmployeeFederalTax } from '../../../../../../main/webapp/app/entities/employee-federal-tax/employee-federal-tax.model';

describe('Component Tests', () => {

    describe('EmployeeFederalTax Management Dialog Component', () => {
        let comp: EmployeeFederalTaxDialogComponent;
        let fixture: ComponentFixture<EmployeeFederalTaxDialogComponent>;
        let service: EmployeeFederalTaxService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [EmployeeFederalTaxDialogComponent],
                providers: [
                    EmployeeFederalTaxService
                ]
            })
            .overrideTemplate(EmployeeFederalTaxDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeFederalTaxDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeFederalTaxService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new EmployeeFederalTax(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.employeeFederalTax = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'employeeFederalTaxListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new EmployeeFederalTax();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.employeeFederalTax = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'employeeFederalTaxListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
