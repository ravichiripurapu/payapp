/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { DeductionTypeDetailComponent } from '../../../../../../main/webapp/app/entities/deduction-type/deduction-type-detail.component';
import { DeductionTypeService } from '../../../../../../main/webapp/app/entities/deduction-type/deduction-type.service';
import { DeductionType } from '../../../../../../main/webapp/app/entities/deduction-type/deduction-type.model';

describe('Component Tests', () => {

    describe('DeductionType Management Detail Component', () => {
        let comp: DeductionTypeDetailComponent;
        let fixture: ComponentFixture<DeductionTypeDetailComponent>;
        let service: DeductionTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [DeductionTypeDetailComponent],
                providers: [
                    DeductionTypeService
                ]
            })
            .overrideTemplate(DeductionTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DeductionTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeductionTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new DeductionType(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.deductionType).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
