/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { PayrollScheduleComponent } from '../../../../../../main/webapp/app/entities/payroll-schedule/payroll-schedule.component';
import { PayrollScheduleService } from '../../../../../../main/webapp/app/entities/payroll-schedule/payroll-schedule.service';
import { PayrollSchedule } from '../../../../../../main/webapp/app/entities/payroll-schedule/payroll-schedule.model';

describe('Component Tests', () => {

    describe('PayrollSchedule Management Component', () => {
        let comp: PayrollScheduleComponent;
        let fixture: ComponentFixture<PayrollScheduleComponent>;
        let service: PayrollScheduleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [PayrollScheduleComponent],
                providers: [
                    PayrollScheduleService
                ]
            })
            .overrideTemplate(PayrollScheduleComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayrollScheduleComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollScheduleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new PayrollSchedule(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.payrollSchedules[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
