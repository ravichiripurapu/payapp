/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { DepositTypeDetailComponent } from '../../../../../../main/webapp/app/entities/deposit-type/deposit-type-detail.component';
import { DepositTypeService } from '../../../../../../main/webapp/app/entities/deposit-type/deposit-type.service';
import { DepositType } from '../../../../../../main/webapp/app/entities/deposit-type/deposit-type.model';

describe('Component Tests', () => {

    describe('DepositType Management Detail Component', () => {
        let comp: DepositTypeDetailComponent;
        let fixture: ComponentFixture<DepositTypeDetailComponent>;
        let service: DepositTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [DepositTypeDetailComponent],
                providers: [
                    DepositTypeService
                ]
            })
            .overrideTemplate(DepositTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DepositTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepositTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new DepositType(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.depositType).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
