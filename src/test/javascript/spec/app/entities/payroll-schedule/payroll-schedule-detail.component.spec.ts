/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { PayrollScheduleDetailComponent } from '../../../../../../main/webapp/app/entities/payroll-schedule/payroll-schedule-detail.component';
import { PayrollScheduleService } from '../../../../../../main/webapp/app/entities/payroll-schedule/payroll-schedule.service';
import { PayrollSchedule } from '../../../../../../main/webapp/app/entities/payroll-schedule/payroll-schedule.model';

describe('Component Tests', () => {

    describe('PayrollSchedule Management Detail Component', () => {
        let comp: PayrollScheduleDetailComponent;
        let fixture: ComponentFixture<PayrollScheduleDetailComponent>;
        let service: PayrollScheduleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [PayrollScheduleDetailComponent],
                providers: [
                    PayrollScheduleService
                ]
            })
            .overrideTemplate(PayrollScheduleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayrollScheduleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollScheduleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new PayrollSchedule(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.payrollSchedule).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
