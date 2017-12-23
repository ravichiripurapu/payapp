/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { PayrollEmployeeTaxesComponent } from '../../../../../../main/webapp/app/entities/payroll-employee-taxes/payroll-employee-taxes.component';
import { PayrollEmployeeTaxesService } from '../../../../../../main/webapp/app/entities/payroll-employee-taxes/payroll-employee-taxes.service';
import { PayrollEmployeeTaxes } from '../../../../../../main/webapp/app/entities/payroll-employee-taxes/payroll-employee-taxes.model';

describe('Component Tests', () => {

    describe('PayrollEmployeeTaxes Management Component', () => {
        let comp: PayrollEmployeeTaxesComponent;
        let fixture: ComponentFixture<PayrollEmployeeTaxesComponent>;
        let service: PayrollEmployeeTaxesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [PayrollEmployeeTaxesComponent],
                providers: [
                    PayrollEmployeeTaxesService
                ]
            })
            .overrideTemplate(PayrollEmployeeTaxesComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayrollEmployeeTaxesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollEmployeeTaxesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new PayrollEmployeeTaxes(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.payrollEmployeeTaxes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
