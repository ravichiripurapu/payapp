/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { PayrollEarningsComponent } from '../../../../../../main/webapp/app/entities/payroll-earnings/payroll-earnings.component';
import { PayrollEarningsService } from '../../../../../../main/webapp/app/entities/payroll-earnings/payroll-earnings.service';
import { PayrollEarnings } from '../../../../../../main/webapp/app/entities/payroll-earnings/payroll-earnings.model';

describe('Component Tests', () => {

    describe('PayrollEarnings Management Component', () => {
        let comp: PayrollEarningsComponent;
        let fixture: ComponentFixture<PayrollEarningsComponent>;
        let service: PayrollEarningsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [PayrollEarningsComponent],
                providers: [
                    PayrollEarningsService
                ]
            })
            .overrideTemplate(PayrollEarningsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayrollEarningsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollEarningsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new PayrollEarnings(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.employeePayEarning[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
