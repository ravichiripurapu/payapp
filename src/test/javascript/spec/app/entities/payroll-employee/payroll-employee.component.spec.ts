/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { PayrollEmployeeComponent } from '../../../../../../main/webapp/app/entities/payroll-employee/payroll-employee.component';
import { PayrollEmployeeService } from '../../../../../../main/webapp/app/entities/payroll-employee/payroll-employee.service';
import { PayrollEmployee } from '../../../../../../main/webapp/app/entities/payroll-employee/payroll-employee.model';

describe('Component Tests', () => {

    describe('PayrollEmployee Management Component', () => {
        let comp: PayrollEmployeeComponent;
        let fixture: ComponentFixture<PayrollEmployeeComponent>;
        let service: PayrollEmployeeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [PayrollEmployeeComponent],
                providers: [
                    PayrollEmployeeService
                ]
            })
            .overrideTemplate(PayrollEmployeeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayrollEmployeeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollEmployeeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new PayrollEmployee(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.payrollEmployees[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
