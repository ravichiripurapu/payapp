/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { EmployeeResidenceLocationDialogComponent } from '../../../../../../main/webapp/app/entities/employee-residence-location/employee-residence-location-dialog.component';
import { EmployeeResidenceLocationService } from '../../../../../../main/webapp/app/entities/employee-residence-location/employee-residence-location.service';
import { EmployeeResidenceLocation } from '../../../../../../main/webapp/app/entities/employee-residence-location/employee-residence-location.model';

describe('Component Tests', () => {

    describe('EmployeeResidenceLocation Management Dialog Component', () => {
        let comp: EmployeeResidenceLocationDialogComponent;
        let fixture: ComponentFixture<EmployeeResidenceLocationDialogComponent>;
        let service: EmployeeResidenceLocationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [EmployeeResidenceLocationDialogComponent],
                providers: [
                    EmployeeResidenceLocationService
                ]
            })
            .overrideTemplate(EmployeeResidenceLocationDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeResidenceLocationDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeResidenceLocationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new EmployeeResidenceLocation(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.employeeResidenceLocation = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'employeeResidenceLocationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new EmployeeResidenceLocation();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.employeeResidenceLocation = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'employeeResidenceLocationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
