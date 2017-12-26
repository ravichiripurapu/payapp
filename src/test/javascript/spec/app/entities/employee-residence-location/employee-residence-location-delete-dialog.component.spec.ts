/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { EmployeeResidenceLocationDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/employee-residence-location/employee-residence-location-delete-dialog.component';
import { EmployeeResidenceLocationService } from '../../../../../../main/webapp/app/entities/employee-residence-location/employee-residence-location.service';

describe('Component Tests', () => {

    describe('EmployeeResidenceLocation Management Delete Component', () => {
        let comp: EmployeeResidenceLocationDeleteDialogComponent;
        let fixture: ComponentFixture<EmployeeResidenceLocationDeleteDialogComponent>;
        let service: EmployeeResidenceLocationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [EmployeeResidenceLocationDeleteDialogComponent],
                providers: [
                    EmployeeResidenceLocationService
                ]
            })
            .overrideTemplate(EmployeeResidenceLocationDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeResidenceLocationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeResidenceLocationService);
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
