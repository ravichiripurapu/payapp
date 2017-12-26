/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { CompanyStateTaxDetailComponent } from '../../../../../../main/webapp/app/entities/company-state-tax/company-state-tax-detail.component';
import { CompanyStateTaxService } from '../../../../../../main/webapp/app/entities/company-state-tax/company-state-tax.service';
import { CompanyStateTax } from '../../../../../../main/webapp/app/entities/company-state-tax/company-state-tax.model';

describe('Component Tests', () => {

    describe('CompanyStateTax Management Detail Component', () => {
        let comp: CompanyStateTaxDetailComponent;
        let fixture: ComponentFixture<CompanyStateTaxDetailComponent>;
        let service: CompanyStateTaxService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyStateTaxDetailComponent],
                providers: [
                    CompanyStateTaxService
                ]
            })
            .overrideTemplate(CompanyStateTaxDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyStateTaxDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyStateTaxService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new CompanyStateTax(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.companyStateTax).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
