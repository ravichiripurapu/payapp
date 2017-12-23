/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { EmployeeBankDetailComponent } from '../../../../../../main/webapp/app/entities/employee-bank/employee-bank-detail.component';
import { EmployeeBankService } from '../../../../../../main/webapp/app/entities/employee-bank/employee-bank.service';
import { EmployeeBank } from '../../../../../../main/webapp/app/entities/employee-bank/employee-bank.model';

describe('Component Tests', () => {

    describe('EmployeeBank Management Detail Component', () => {
        let comp: EmployeeBankDetailComponent;
        let fixture: ComponentFixture<EmployeeBankDetailComponent>;
        let service: EmployeeBankService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [EmployeeBankDetailComponent],
                providers: [
                    EmployeeBankService
                ]
            })
            .overrideTemplate(EmployeeBankDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeBankDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeBankService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new EmployeeBank(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.employeeBank).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
