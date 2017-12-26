/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { PayrollEmployeeTaxesDetailComponent } from '../../../../../../main/webapp/app/entities/payroll-employee-taxes/payroll-employee-taxes-detail.component';
import { PayrollEmployeeTaxesService } from '../../../../../../main/webapp/app/entities/payroll-employee-taxes/payroll-employee-taxes.service';
import { PayrollEmployeeTaxes } from '../../../../../../main/webapp/app/entities/payroll-employee-taxes/payroll-employee-taxes.model';

describe('Component Tests', () => {

    describe('PayrollEmployeeTaxes Management Detail Component', () => {
        let comp: PayrollEmployeeTaxesDetailComponent;
        let fixture: ComponentFixture<PayrollEmployeeTaxesDetailComponent>;
        let service: PayrollEmployeeTaxesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [PayrollEmployeeTaxesDetailComponent],
                providers: [
                    PayrollEmployeeTaxesService
                ]
            })
            .overrideTemplate(PayrollEmployeeTaxesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayrollEmployeeTaxesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollEmployeeTaxesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new PayrollEmployeeTaxes(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.payrollEmployeeTaxes).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
