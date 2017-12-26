/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { EmployeeStateTaxComponent } from '../../../../../../main/webapp/app/entities/employee-state-tax/employee-state-tax.component';
import { EmployeeStateTaxService } from '../../../../../../main/webapp/app/entities/employee-state-tax/employee-state-tax.service';
import { EmployeeStateTax } from '../../../../../../main/webapp/app/entities/employee-state-tax/employee-state-tax.model';

describe('Component Tests', () => {

    describe('EmployeeStateTax Management Component', () => {
        let comp: EmployeeStateTaxComponent;
        let fixture: ComponentFixture<EmployeeStateTaxComponent>;
        let service: EmployeeStateTaxService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [EmployeeStateTaxComponent],
                providers: [
                    EmployeeStateTaxService
                ]
            })
            .overrideTemplate(EmployeeStateTaxComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeStateTaxComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeStateTaxService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new EmployeeStateTax(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.employeeStateTaxes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
