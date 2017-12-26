/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { CompanyDeductionDetailComponent } from '../../../../../../main/webapp/app/entities/company-deduction/company-deduction-detail.component';
import { CompanyDeductionService } from '../../../../../../main/webapp/app/entities/company-deduction/company-deduction.service';
import { CompanyDeduction } from '../../../../../../main/webapp/app/entities/company-deduction/company-deduction.model';

describe('Component Tests', () => {

    describe('CompanyDeduction Management Detail Component', () => {
        let comp: CompanyDeductionDetailComponent;
        let fixture: ComponentFixture<CompanyDeductionDetailComponent>;
        let service: CompanyDeductionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyDeductionDetailComponent],
                providers: [
                    CompanyDeductionService
                ]
            })
            .overrideTemplate(CompanyDeductionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyDeductionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyDeductionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new CompanyDeduction(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.companyDeduction).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
