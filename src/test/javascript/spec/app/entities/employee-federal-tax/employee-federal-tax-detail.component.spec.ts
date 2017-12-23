/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { EmployeeFederalTaxDetailComponent } from '../../../../../../main/webapp/app/entities/employee-federal-tax/employee-federal-tax-detail.component';
import { EmployeeFederalTaxService } from '../../../../../../main/webapp/app/entities/employee-federal-tax/employee-federal-tax.service';
import { EmployeeFederalTax } from '../../../../../../main/webapp/app/entities/employee-federal-tax/employee-federal-tax.model';

describe('Component Tests', () => {

    describe('EmployeeFederalTax Management Detail Component', () => {
        let comp: EmployeeFederalTaxDetailComponent;
        let fixture: ComponentFixture<EmployeeFederalTaxDetailComponent>;
        let service: EmployeeFederalTaxService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [EmployeeFederalTaxDetailComponent],
                providers: [
                    EmployeeFederalTaxService
                ]
            })
            .overrideTemplate(EmployeeFederalTaxDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeFederalTaxDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeFederalTaxService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new EmployeeFederalTax(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.employeeFederalTax).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
