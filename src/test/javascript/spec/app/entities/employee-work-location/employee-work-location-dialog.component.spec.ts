/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { EmployeeWorkLocationDialogComponent } from '../../../../../../main/webapp/app/entities/employee-work-location/employee-work-location-dialog.component';
import { EmployeeWorkLocationService } from '../../../../../../main/webapp/app/entities/employee-work-location/employee-work-location.service';
import { EmployeeWorkLocation } from '../../../../../../main/webapp/app/entities/employee-work-location/employee-work-location.model';

describe('Component Tests', () => {

    describe('EmployeeWorkLocation Management Dialog Component', () => {
        let comp: EmployeeWorkLocationDialogComponent;
        let fixture: ComponentFixture<EmployeeWorkLocationDialogComponent>;
        let service: EmployeeWorkLocationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [EmployeeWorkLocationDialogComponent],
                providers: [
                    EmployeeWorkLocationService
                ]
            })
            .overrideTemplate(EmployeeWorkLocationDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeWorkLocationDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeWorkLocationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new EmployeeWorkLocation(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.employeeWorkLocation = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'employeeWorkLocationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new EmployeeWorkLocation();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.employeeWorkLocation = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'employeeWorkLocationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
