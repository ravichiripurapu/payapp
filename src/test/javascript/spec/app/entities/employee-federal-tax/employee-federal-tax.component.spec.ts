/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { EmployeeFederalTaxComponent } from '../../../../../../main/webapp/app/entities/employee-federal-tax/employee-federal-tax.component';
import { EmployeeFederalTaxService } from '../../../../../../main/webapp/app/entities/employee-federal-tax/employee-federal-tax.service';
import { EmployeeFederalTax } from '../../../../../../main/webapp/app/entities/employee-federal-tax/employee-federal-tax.model';

describe('Component Tests', () => {

    describe('EmployeeFederalTax Management Component', () => {
        let comp: EmployeeFederalTaxComponent;
        let fixture: ComponentFixture<EmployeeFederalTaxComponent>;
        let service: EmployeeFederalTaxService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [EmployeeFederalTaxComponent],
                providers: [
                    EmployeeFederalTaxService
                ]
            })
            .overrideTemplate(EmployeeFederalTaxComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeFederalTaxComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeFederalTaxService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new EmployeeFederalTax(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.employeeFederalTaxes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
