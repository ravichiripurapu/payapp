/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { CompanyLocalTaxDetailComponent } from '../../../../../../main/webapp/app/entities/company-local-tax/company-local-tax-detail.component';
import { CompanyLocalTaxService } from '../../../../../../main/webapp/app/entities/company-local-tax/company-local-tax.service';
import { CompanyLocalTax } from '../../../../../../main/webapp/app/entities/company-local-tax/company-local-tax.model';

describe('Component Tests', () => {

    describe('CompanyLocalTax Management Detail Component', () => {
        let comp: CompanyLocalTaxDetailComponent;
        let fixture: ComponentFixture<CompanyLocalTaxDetailComponent>;
        let service: CompanyLocalTaxService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyLocalTaxDetailComponent],
                providers: [
                    CompanyLocalTaxService
                ]
            })
            .overrideTemplate(CompanyLocalTaxDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyLocalTaxDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyLocalTaxService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new CompanyLocalTax(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.companyLocalTax).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
