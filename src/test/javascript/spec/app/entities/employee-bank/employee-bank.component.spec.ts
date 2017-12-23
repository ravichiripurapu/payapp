/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { EmployeeBankComponent } from '../../../../../../main/webapp/app/entities/employee-bank/employee-bank.component';
import { EmployeeBankService } from '../../../../../../main/webapp/app/entities/employee-bank/employee-bank.service';
import { EmployeeBank } from '../../../../../../main/webapp/app/entities/employee-bank/employee-bank.model';

describe('Component Tests', () => {

    describe('EmployeeBank Management Component', () => {
        let comp: EmployeeBankComponent;
        let fixture: ComponentFixture<EmployeeBankComponent>;
        let service: EmployeeBankService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [EmployeeBankComponent],
                providers: [
                    EmployeeBankService
                ]
            })
            .overrideTemplate(EmployeeBankComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeBankComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeBankService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new EmployeeBank(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.employeeBanks[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
