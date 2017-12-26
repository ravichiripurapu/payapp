/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { CompanyContactTypeDetailComponent } from '../../../../../../main/webapp/app/entities/company-contact-type/company-contact-type-detail.component';
import { CompanyContactTypeService } from '../../../../../../main/webapp/app/entities/company-contact-type/company-contact-type.service';
import { CompanyContactType } from '../../../../../../main/webapp/app/entities/company-contact-type/company-contact-type.model';

describe('Component Tests', () => {

    describe('CompanyContactType Management Detail Component', () => {
        let comp: CompanyContactTypeDetailComponent;
        let fixture: ComponentFixture<CompanyContactTypeDetailComponent>;
        let service: CompanyContactTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyContactTypeDetailComponent],
                providers: [
                    CompanyContactTypeService
                ]
            })
            .overrideTemplate(CompanyContactTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyContactTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyContactTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new CompanyContactType(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.companyContactType).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
