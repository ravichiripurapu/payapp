/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { EmployeeLocalTaxComponent } from '../../../../../../main/webapp/app/entities/employee-local-tax/employee-local-tax.component';
import { EmployeeLocalTaxService } from '../../../../../../main/webapp/app/entities/employee-local-tax/employee-local-tax.service';
import { EmployeeLocalTax } from '../../../../../../main/webapp/app/entities/employee-local-tax/employee-local-tax.model';

describe('Component Tests', () => {

    describe('EmployeeLocalTax Management Component', () => {
        let comp: EmployeeLocalTaxComponent;
        let fixture: ComponentFixture<EmployeeLocalTaxComponent>;
        let service: EmployeeLocalTaxService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [EmployeeLocalTaxComponent],
                providers: [
                    EmployeeLocalTaxService
                ]
            })
            .overrideTemplate(EmployeeLocalTaxComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeLocalTaxComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeLocalTaxService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new EmployeeLocalTax(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.employeeLocalTaxes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
