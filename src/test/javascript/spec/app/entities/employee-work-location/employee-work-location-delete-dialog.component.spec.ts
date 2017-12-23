/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { EmployeeWorkLocationDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/employee-work-location/employee-work-location-delete-dialog.component';
import { EmployeeWorkLocationService } from '../../../../../../main/webapp/app/entities/employee-work-location/employee-work-location.service';

describe('Component Tests', () => {

    describe('EmployeeWorkLocation Management Delete Component', () => {
        let comp: EmployeeWorkLocationDeleteDialogComponent;
        let fixture: ComponentFixture<EmployeeWorkLocationDeleteDialogComponent>;
        let service: EmployeeWorkLocationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [EmployeeWorkLocationDeleteDialogComponent],
                providers: [
                    EmployeeWorkLocationService
                ]
            })
            .overrideTemplate(EmployeeWorkLocationDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeWorkLocationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeWorkLocationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
