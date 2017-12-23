/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { PayrollEmployerTaxesDetailComponent } from '../../../../../../main/webapp/app/entities/payroll-employer-taxes/payroll-employer-taxes-detail.component';
import { PayrollEmployerTaxesService } from '../../../../../../main/webapp/app/entities/payroll-employer-taxes/payroll-employer-taxes.service';
import { PayrollEmployerTaxes } from '../../../../../../main/webapp/app/entities/payroll-employer-taxes/payroll-employer-taxes.model';

describe('Component Tests', () => {

    describe('PayrollEmployerTaxes Management Detail Component', () => {
        let comp: PayrollEmployerTaxesDetailComponent;
        let fixture: ComponentFixture<PayrollEmployerTaxesDetailComponent>;
        let service: PayrollEmployerTaxesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [PayrollEmployerTaxesDetailComponent],
                providers: [
                    PayrollEmployerTaxesService
                ]
            })
            .overrideTemplate(PayrollEmployerTaxesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayrollEmployerTaxesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollEmployerTaxesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new PayrollEmployerTaxes(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.payrollEmployerTaxes).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
