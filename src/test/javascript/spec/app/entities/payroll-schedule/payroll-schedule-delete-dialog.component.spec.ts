/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PayappTestModule } from '../../../test.module';
import { PayrollScheduleDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/payroll-schedule/payroll-schedule-delete-dialog.component';
import { PayrollScheduleService } from '../../../../../../main/webapp/app/entities/payroll-schedule/payroll-schedule.service';

describe('Component Tests', () => {

    describe('PayrollSchedule Management Delete Component', () => {
        let comp: PayrollScheduleDeleteDialogComponent;
        let fixture: ComponentFixture<PayrollScheduleDeleteDialogComponent>;
        let service: PayrollScheduleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [PayrollScheduleDeleteDialogComponent],
                providers: [
                    PayrollScheduleService
                ]
            })
            .overrideTemplate(PayrollScheduleDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayrollScheduleDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollScheduleService);
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
