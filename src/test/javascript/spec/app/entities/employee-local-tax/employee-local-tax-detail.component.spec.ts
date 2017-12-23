/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { EmployeeLocalTaxDetailComponent } from '../../../../../../main/webapp/app/entities/employee-local-tax/employee-local-tax-detail.component';
import { EmployeeLocalTaxService } from '../../../../../../main/webapp/app/entities/employee-local-tax/employee-local-tax.service';
import { EmployeeLocalTax } from '../../../../../../main/webapp/app/entities/employee-local-tax/employee-local-tax.model';

describe('Component Tests', () => {

    describe('EmployeeLocalTax Management Detail Component', () => {
        let comp: EmployeeLocalTaxDetailComponent;
        let fixture: ComponentFixture<EmployeeLocalTaxDetailComponent>;
        let service: EmployeeLocalTaxService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [EmployeeLocalTaxDetailComponent],
                providers: [
                    EmployeeLocalTaxService
                ]
            })
            .overrideTemplate(EmployeeLocalTaxDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeLocalTaxDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeLocalTaxService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new EmployeeLocalTax(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.employeeLocalTax).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
