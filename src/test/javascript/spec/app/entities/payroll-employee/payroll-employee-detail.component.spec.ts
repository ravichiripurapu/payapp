/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { PayrollEmployeeDetailComponent } from '../../../../../../main/webapp/app/entities/payroll-employee/payroll-employee-detail.component';
import { PayrollEmployeeService } from '../../../../../../main/webapp/app/entities/payroll-employee/payroll-employee.service';
import { PayrollEmployee } from '../../../../../../main/webapp/app/entities/payroll-employee/payroll-employee.model';

describe('Component Tests', () => {

    describe('PayrollEmployee Management Detail Component', () => {
        let comp: PayrollEmployeeDetailComponent;
        let fixture: ComponentFixture<PayrollEmployeeDetailComponent>;
        let service: PayrollEmployeeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [PayrollEmployeeDetailComponent],
                providers: [
                    PayrollEmployeeService
                ]
            })
            .overrideTemplate(PayrollEmployeeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayrollEmployeeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollEmployeeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new PayrollEmployee(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.payrollEmployee).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
