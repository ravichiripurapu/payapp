/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { CompanyLocalTaxComponent } from '../../../../../../main/webapp/app/entities/company-local-tax/company-local-tax.component';
import { CompanyLocalTaxService } from '../../../../../../main/webapp/app/entities/company-local-tax/company-local-tax.service';
import { CompanyLocalTax } from '../../../../../../main/webapp/app/entities/company-local-tax/company-local-tax.model';

describe('Component Tests', () => {

    describe('CompanyLocalTax Management Component', () => {
        let comp: CompanyLocalTaxComponent;
        let fixture: ComponentFixture<CompanyLocalTaxComponent>;
        let service: CompanyLocalTaxService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyLocalTaxComponent],
                providers: [
                    CompanyLocalTaxService
                ]
            })
            .overrideTemplate(CompanyLocalTaxComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyLocalTaxComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyLocalTaxService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new CompanyLocalTax(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.companyLocalTaxes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
