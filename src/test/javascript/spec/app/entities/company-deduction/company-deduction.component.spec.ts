/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { CompanyDeductionComponent } from '../../../../../../main/webapp/app/entities/company-deduction/company-deduction.component';
import { CompanyDeductionService } from '../../../../../../main/webapp/app/entities/company-deduction/company-deduction.service';
import { CompanyDeduction } from '../../../../../../main/webapp/app/entities/company-deduction/company-deduction.model';

describe('Component Tests', () => {

    describe('CompanyDeduction Management Component', () => {
        let comp: CompanyDeductionComponent;
        let fixture: ComponentFixture<CompanyDeductionComponent>;
        let service: CompanyDeductionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyDeductionComponent],
                providers: [
                    CompanyDeductionService
                ]
            })
            .overrideTemplate(CompanyDeductionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyDeductionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyDeductionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new CompanyDeduction(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.companyDeductions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
