/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { EmployeeStateTaxDialogComponent } from '../../../../../../main/webapp/app/entities/employee-state-tax/employee-state-tax-dialog.component';
import { EmployeeStateTaxService } from '../../../../../../main/webapp/app/entities/employee-state-tax/employee-state-tax.service';
import { EmployeeStateTax } from '../../../../../../main/webapp/app/entities/employee-state-tax/employee-state-tax.model';

describe('Component Tests', () => {

    describe('EmployeeStateTax Management Dialog Component', () => {
        let comp: EmployeeStateTaxDialogComponent;
        let fixture: ComponentFixture<EmployeeStateTaxDialogComponent>;
        let service: EmployeeStateTaxService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [EmployeeStateTaxDialogComponent],
                providers: [
                    EmployeeStateTaxService
                ]
            })
            .overrideTemplate(EmployeeStateTaxDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeStateTaxDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeStateTaxService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new EmployeeStateTax(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.employeeStateTax = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'employeeStateTaxListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new EmployeeStateTax();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.employeeStateTax = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'employeeStateTaxListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
