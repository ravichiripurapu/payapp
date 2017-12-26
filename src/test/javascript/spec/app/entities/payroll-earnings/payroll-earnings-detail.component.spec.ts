/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { PayrollEarningsDetailComponent } from '../../../../../../main/webapp/app/entities/payroll-earnings/payroll-earnings-detail.component';
import { PayrollEarningsService } from '../../../../../../main/webapp/app/entities/payroll-earnings/payroll-earnings.service';
import { PayrollEarnings } from '../../../../../../main/webapp/app/entities/payroll-earnings/payroll-earnings.model';

describe('Component Tests', () => {

    describe('PayrollEarnings Management Detail Component', () => {
        let comp: PayrollEarningsDetailComponent;
        let fixture: ComponentFixture<PayrollEarningsDetailComponent>;
        let service: PayrollEarningsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [PayrollEarningsDetailComponent],
                providers: [
                    PayrollEarningsService
                ]
            })
            .overrideTemplate(PayrollEarningsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayrollEarningsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollEarningsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new PayrollEarnings(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.payrollEarnings).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
