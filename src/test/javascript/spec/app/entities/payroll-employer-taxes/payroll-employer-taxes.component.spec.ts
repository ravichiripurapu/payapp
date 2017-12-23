/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { PayrollEmployerTaxesComponent } from '../../../../../../main/webapp/app/entities/payroll-employer-taxes/payroll-employer-taxes.component';
import { PayrollEmployerTaxesService } from '../../../../../../main/webapp/app/entities/payroll-employer-taxes/payroll-employer-taxes.service';
import { PayrollEmployerTaxes } from '../../../../../../main/webapp/app/entities/payroll-employer-taxes/payroll-employer-taxes.model';

describe('Component Tests', () => {

    describe('PayrollEmployerTaxes Management Component', () => {
        let comp: PayrollEmployerTaxesComponent;
        let fixture: ComponentFixture<PayrollEmployerTaxesComponent>;
        let service: PayrollEmployerTaxesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [PayrollEmployerTaxesComponent],
                providers: [
                    PayrollEmployerTaxesService
                ]
            })
            .overrideTemplate(PayrollEmployerTaxesComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayrollEmployerTaxesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayrollEmployerTaxesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new PayrollEmployerTaxes(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.payrollEmployerTaxes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
