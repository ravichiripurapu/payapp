/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { EmployeeStateTaxDetailComponent } from '../../../../../../main/webapp/app/entities/employee-state-tax/employee-state-tax-detail.component';
import { EmployeeStateTaxService } from '../../../../../../main/webapp/app/entities/employee-state-tax/employee-state-tax.service';
import { EmployeeStateTax } from '../../../../../../main/webapp/app/entities/employee-state-tax/employee-state-tax.model';

describe('Component Tests', () => {

    describe('EmployeeStateTax Management Detail Component', () => {
        let comp: EmployeeStateTaxDetailComponent;
        let fixture: ComponentFixture<EmployeeStateTaxDetailComponent>;
        let service: EmployeeStateTaxService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [EmployeeStateTaxDetailComponent],
                providers: [
                    EmployeeStateTaxService
                ]
            })
            .overrideTemplate(EmployeeStateTaxDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeStateTaxDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeStateTaxService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new EmployeeStateTax(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.employeeStateTax).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
